package jp.glory.todo.context.console.web.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import jp.glory.todo.context.base.web.page.PagePaths;

/**
 * 管理コンソールページ.
 * 
 * @author Junki Yamada
 *
 */
@Controller
@RequestMapping(value = PagePaths.Console.PATH, method = RequestMethod.GET)
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class ConsolePage {

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

        return "console/console";
    }
}
