var JoinForm = Vue.extend({
	template: "#join-form",
	data: {
		function() {
			return {
				user: {
					loginId: "",
					userName: "",
					password: ""
				}
			};
		}
	},
	methods: {
		join: function() {

			var that = this;
			window.superagent
				.post("/api/account")
				.type("form")
				.set(CommonProps.csrfHeaderName, CommonProps.csrfToken)
				.send(this.user)
				.end(function(err, res) {

					if (res.status == 201) {

						that._forwardComplete();
					}

					if (res.status == 400) {

						that.$refs.registerErrors.add(res.body.errors);
					}
				});
			
		},
		_forwardComplete: function() {

			this.$router.go({
				name: "complete",
				params : {
					loginId: this.user.loginId
				}
			});
		}
	}
});
Vue.component("join-form", JoinForm);