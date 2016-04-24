/**
 * TODOのSPA.
 * @required props.js
 */
var TodoApps = Vue.extend({
	props: {
		"csrf-token": {
			type: String,
			required: true
		},
		"csrf-header-name": {
			type: String,
			required: true
		}
	},
	ready: function() {
		CommonProps.csrfToken = this.csrfToken;
		CommonProps.csrfHeaderName = this.csrfHeaderName;
	}
});
var router = new VueRouter();
router.map({
	"/": {
		component: TodoList
	},
	"/register": {
		name: "register",
		component: TodoRegister
	},
	"/edit/:id": {
		name: "edit",
		component: TodoRegister
	},
	"/view/:id": {
		name: "detail",
		component: TodoDetail
	}
});

router.start(TodoApps, "#todos");