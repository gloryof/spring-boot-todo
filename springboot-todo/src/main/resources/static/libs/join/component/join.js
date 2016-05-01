/**
 * アカウント登録のSPA.
 * @required props.js
 */
var JoinApps = Vue.extend({
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
		component: JoinForm
	},
	"/complete/:loginId": {
		name: "complete",
		component: JoinComplete
	}
});

router.start(JoinApps, "#join");