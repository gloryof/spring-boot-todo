/**
 * 管理コンソールのSPA.
 * @required props.js
 */
var ConsoleTop = Vue.extend({});
var router = new VueRouter();
router.map({
	"/trace": {
		component: Trace
	}
});

router.start(ConsoleTop, "#console");