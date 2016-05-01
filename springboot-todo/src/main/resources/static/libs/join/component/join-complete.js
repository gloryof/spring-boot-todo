var JoinComplete = Vue.extend({
	template: "#join-complete",
	data: function() {
		return {
			user: {
				loginId: ""
			}
		};
	},
	ready: function() {
		this.user.loginId = this.$route.params.loginId;
	}
});
Vue.component("join-complete", JoinComplete);