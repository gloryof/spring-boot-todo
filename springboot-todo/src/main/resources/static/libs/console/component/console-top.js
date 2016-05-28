/**
 * 管理コンソールのSPA.
 * @required props.js
 */
var ConsoleTop = Vue.extend({});
var router = new VueRouter();
router.map({
	"/trace": {
		component: Trace
	},
	"/metrics": {
		component: Metrics
	},
});

router.start(ConsoleTop, "#console");