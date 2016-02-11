package jp.glory.web.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.glory.web.session.UserInfo;

/**
 * コンストラクタ.
 * 
 * @author Junki Yamada
 *
 */
@Controller
@RequestMapping(value = PagePaths.Todo.PATH, method = RequestMethod.GET)
public class TodoPage {

    /**
     * ユーザ情報.
     */
    private final UserInfo userInfo;

    /**
     * コンストラクタ.
     * 
     * @param userInfo
     *            ユーザ情報
     */
    @Autowired
    public TodoPage(final UserInfo userInfo) {

        this.userInfo = userInfo;
    }

    /**
     * ページ表示処理.
     * 
     * @return TODOページ.
     */
    @RequestMapping
    public ModelAndView view() {

        return new ModelAndView("/todos/todos");
    }
}
