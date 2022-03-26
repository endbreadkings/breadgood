package com.bside.breadgood.ddd.users.fixtures;

import com.bside.breadgood.ddd.users.domain.Email;
import com.bside.breadgood.ddd.users.domain.NickName;
import com.bside.breadgood.ddd.users.domain.Role;
import com.bside.breadgood.ddd.users.domain.User;

/**
 * author : haedoang
 * date : 2022/03/23
 * description :
 */
public class UserFixtures {
    public static final User 테스트유저 =
            new User(
                    1L,
                    NickName.valueOf("테스트유저"),
                    Email.valueOf("test@breadgood.com"),
                    "1234",
                    1L,
                    null,
                    null,
                    Role.USER
            );

}
