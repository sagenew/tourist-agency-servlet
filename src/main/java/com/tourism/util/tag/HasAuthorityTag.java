package com.tourism.util.tag;

import com.tourism.model.entity.User;
import com.tourism.model.entity.enums.Authority;

import javax.servlet.jsp.tagext.TagSupport;
import java.util.Objects;

public class HasAuthorityTag extends TagSupport {
    private String authority = "";

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public int doStartTag() {
        User authUser = (User) pageContext.getSession().getAttribute("authUser");
        if (Objects.nonNull(authUser)
                && authUser.getAuthorities().contains(Authority.valueOf(authority))
        ) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}

