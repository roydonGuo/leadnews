package com.roydon.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.model.common.dto.ResponseResult;
import com.roydon.model.common.enums.AppHttpCodeEnum;
import com.roydon.model.user.dto.LoginDto;
import com.roydon.model.user.pojo.ApUser;
import com.roydon.user.mapper.ApUserMapper;
import com.roydon.user.service.ApUserService;
import com.roydon.utils.common.AppJwtUtil;
import com.roydon.utils.common.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {
    /**
     * app端登录功能
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(LoginDto dto) {
        //1.正常登录 用户名和密码
        if (StringUtils.isNotBlank(dto.getPhone()) && StringUtils.isNotBlank(dto.getPassword())) {
            //1.1 根据手机号查询用户信息
            LambdaQueryWrapper<ApUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ApUser::getPhone, dto.getPhone());
            ApUser dbUser = null;
            try {
                dbUser = getOne(queryWrapper);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "服务器内部错误");
            }
            if (StringUtils.isNull(dbUser)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户信息不存在");
            }
            //1.2 比对密码
            String salt = dbUser.getSalt();
            String password = dto.getPassword();
            String enPasswd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
            if (!enPasswd.equals(dbUser.getPassword())) {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
            //1.3 返回数据  jwt  user
            String token = AppJwtUtil.getToken(dbUser.getId().longValue());
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            dbUser.setSalt(null);
            dbUser.setPassword(null);
            map.put("user", dbUser);
            return ResponseResult.okResult(map);
        } else {
            //2.游客登录
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(0L));
            return ResponseResult.okResult(map);
        }

    }
}
