package jp.glory.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * ログインページ.
 * 
 * @author Junki Yamada
 *
 */
@Controller
@RequestMapping(value = PagePaths.Login.PATH, method = RequestMethod.GET)
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

        return "/login/login";
    }
}
