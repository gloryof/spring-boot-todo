package jp.glory.todo.context.user.web.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import jp.glory.todo.context.base.web.page.PagePaths;

/**
 * ログインページ.
 * 
 * @author Junki Yamada
 *
 */
@Controller
@RequestMapping(value = PagePaths.Login.PATH, method = RequestMethod.GET)
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class LoginPage {

    /**
     * ログインページを表示する.
     * 
     * @return ログインページ
     */
    @RequestMapping
    public ModelAndView view() {

        return new ModelAndView(getTemplatePath());
    }

    /**
     * テンプレートのパス.
     * 
     * @return パス
     */
    private String getTemplatePath() {

        return "login/login";
    }
}
