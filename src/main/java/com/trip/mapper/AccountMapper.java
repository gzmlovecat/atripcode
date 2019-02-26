package com.trip.mapper;

import com.trip.dto.AccountDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {

    public AccountDto checkUserAndPwd(@Param("username") String  username, @Param("password") String  password);

    public AccountDto checkUserByOpenId(@Param("openId") String openId);

    public void insertAccount(AccountDto account);

    public int updateAccount(@Param("account") AccountDto account);

    public List<AccountDto> selectAccountByParam(@Param("account") AccountDto account);

    public List<AccountDto> selectAccountJoinByParam(@Param("username") String username,
                                                     @Param("employeeName") String employeeName,
                                                     @Param("agentName") String agentName);


    public int updateConfig(@Param("param") AccountDto param);
    
    public int updateAvatarUtl(@Param("avatarUrl")String avatarUrl, @Param("openId")String openId);
    
    public int updateAvatarUtilById(@Param("avatarUrl")String avatarUrl, @Param("id")Long id);
    
    // 判断open_id和手机是否匹配
    public int selectAccountCountByParma(@Param("openId")String openId, @Param("telPhone")String telPhone);
    // 根据open_id和手机号找到用户
    public AccountDto checkUserByOpenIdTelPhone(@Param("openId") String openId, @Param("telPhone")String telPhone);
    // 更新昵称和微信用户头像
    public int updateAccountByParam(@Param("nickName")String nickName, @Param("username")String username, @Param("id")Long id);
    // 根据openId找手机号
    public String selectTelPhoneByOpenId(@Param("openId")String openId);
    // 根据代理ID，取得用户信息
    public AccountDto getAccountInfoByAgentId(@Param("agentId") Long agentId);
}
