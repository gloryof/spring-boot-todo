package jp.glory.web.page;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import jp.glory.web.session.UserInfo;

/**
 * TODOページ.
 * 
 * @author Junki Yamada
 *
 */
@Controller
@RequestMapping(value = PagePaths.Todo.PATH, method = RequestMethod.GET)
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class TodoPage {

    /**
     * ページ表示処理.
     * 
     * @return TODOページ.
     */
    @RequestMapping
    public ModelAndView view(@AuthenticationPrincipal final UserInfo userInfo) {

        return new ModelAndView("/todos/todos");
    }
}
