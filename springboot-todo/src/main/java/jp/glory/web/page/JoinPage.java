package jp.glory.web.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

/**
 * アカウント作成ページ.
 * 
 * @author Junki Yamada
 *
 */
@Controller
@RequestMapping(value = PagePaths.Join.PATH, method = RequestMethod.GET)
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class JoinPage {

    /**
     * アカウント作成ページを表示する.
     * 
     * @return アカウント作成ページ
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

        return "/join/join";
    }
}
