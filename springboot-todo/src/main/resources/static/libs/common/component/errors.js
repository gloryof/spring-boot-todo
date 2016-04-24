/**
 * エラー表示のコンポーネント
 */
var Errors = Vue.extend({
	template: "#common-errors",
	data: function() {

		return {
			details: []
		};
	},
	ready: function() {
	},
	methods: {
		add: function(messages) {
	
			var newErrors = [];
			messages.forEach(function(item) {
				newErrors.push(item);
			});
	
			this.$set("details", newErrors);
		}
	}
});
Vue.component("errors", Errors);