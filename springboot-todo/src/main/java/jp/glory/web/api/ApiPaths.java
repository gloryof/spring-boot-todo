package jp.glory.web.api;

/**
 * APIのパス.
 * 
 * @author Junki Yamada
 *
 */
public final class ApiPaths {

    /**
     * ベースパス.
     */
    private static final String ROOT = "/api";

    /**
     * アカウントAPI.
     * 
     * @author Junki Yamada
     *
     */
    public static class Account {

        /**
         * ベースパス.
         */
        public static final String PATH = ROOT + "/account";
    }

    /**
     * TODOのAPI.
     * 
     * @author Junki Yamada
     *
     */
    public static class Todo {

        /**
         * ベースパス.
         */
        public static final String PATH = ROOT + "/todos";
    }
}
