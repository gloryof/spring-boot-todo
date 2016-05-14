/**
 * エラー表示のコンポーネント
 */
var Errors = Vue.extend({
	template: "#common-errors",
	data: function() {

		return {
			generalError: {
				details: [],
			},
			conflictError: {
				isError: false,
				message: "",
				linkLabel: ""
			}
		};
	},
	ready: function() {
	},
	methods: {
		add: function(messages) {
	
			this._clear();
			var newErrors = [];
			messages.forEach(function(item) {
				newErrors.push(item);
			});

			this.generalError.details = newErrors;
		},
		/**
		 * コンフリクトエラーを表示する.
		 * @param {String} message メッセージ
		 * @param - {String} linkLabel リンクのラベル
		 */
		conflict: function(message, linkLabel) {

			this._clear();
			this.conflictError.isError = true;
			this.conflictError.message = message;
			this.conflictError.linkLabel = linkLabel;
		},
		reload: function() {
			location.reload();
		},
		_clear: function() {

			this.generalError.details = [];

			this.conflictError.isError = false;
			this.conflictError.message = "";
			this.conflictError.linkLabel = "";
		}
	}
});
Vue.component("errors", Errors);