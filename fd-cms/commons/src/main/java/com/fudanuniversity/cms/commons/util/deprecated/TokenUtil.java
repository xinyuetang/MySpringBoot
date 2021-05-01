//package com.fudanuniversity.cms.commons.util;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.fudanuniversity.cms.inner.entity.User;
//
//import java.util.Date;
//
//public class TokenUtil {
//
//    private static final long EXPIRE_TIME= 999*12*60*60*1000;//token到期时间12小时 TODO： 记得删掉999
//    private static final String TOKEN_SECRET="ljdyaishijin**3nkjnj??";  //密钥盐
//    private static final JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
//
//    /**
//     * @Description  ：生成token
//     * @author       : lj
//     * @param        : [user]
//     * @return       : java.lang.String
//     * @exception    :
//     * @date         : 2020-1-31 22:49
//     */
//    public static String sign(User user){
//
//        String token=null;
//        try {
//            Date expireAt=new Date(System.currentTimeMillis()+EXPIRE_TIME);
//            token = JWT.create()
//                    .withIssuer("auth0")//发行人
//                    .withClaim("username",user.getName())
//                    .withClaim("userId",user.getId())//存放数据
//                    .withClaim("roleId",user.getRoleID())
//                    .withExpiresAt(expireAt)//过期时间
//                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
//        } catch (IllegalArgumentException|JWTCreationException je) {
//
//        }
//        return token;
//    }
//
//
//    /**
//     * @Description  ：token验证
//     * @author       : xinyuetang
//     * @param        : [token]
//     * @return       : java.lang.Boolean
//     * @exception    :
//     * @date         : 2021-3-1 22:59
//     */
//    public static Boolean verify(String token){
//
//        try {
//           DecodedJWT decodedJWT=jwtVerifier.verify(token);
//           System.out.println("通过验证，username is : "+ decodedJWT.getClaim("username").asString());
//        } catch (IllegalArgumentException |JWTVerificationException e) {
//            //抛出错误即为验证不通过
//            return false;
//        }
//        return true;
//    }
//
//    //获取发起请求的用户的权限ID
//    public static Integer getRoleId(String token){
//        DecodedJWT decodedJWT=jwtVerifier.verify(token);
//        return  decodedJWT.getClaim("roleId").asInt();
//    }
//
//    //获取发起请求的用户的用户ID
//    public static Integer getUserId(String token){
//        DecodedJWT decodedJWT=jwtVerifier.verify(token);
//        return  decodedJWT.getClaim("userId").asInt();
//    }
//
//
//}
//
