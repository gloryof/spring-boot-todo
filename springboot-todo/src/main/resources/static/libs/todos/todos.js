var TodoApps = Vue.extend({});
var router = new VueRouter();
router.map({
	"/": {
		component: TodoList
	}
});

router.start(TodoApps, "#todos");