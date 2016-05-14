/**
 * TODO登録コンポーネント.
 * @required props.js
 * @required todo.js
 */
var TodoRegister = Vue.extend({
	template: "#todo-register",
	data: function() {

		return {
			/**
			 * TODOの内容に関する情報.
			 */
			todo: {

				/**
				 * TODOのID.
				 * 新規登録時にはnullが、編集時には編集対象のIDが設定される。
				 */
				id : null,

				/** 概要. */
				summary: "",

				/** メモ */
				memo: "",

				/** version */
				version: null,

				/** 完了フラグ. */
				completed: false
			},

			/** 処理に必要なURL. */
			url: {
				/** TODO操作のAPI */
				base: "/api/todos"
			}
		}
	},
	ready: function() {

		if (this.$route.params.id != null) {

			var that = this;
			window.superagent
				.get(this.url.base + "/" + this.$route.params.id)
				.type("form")
				.end(function(err, res) {
					that.todo = res.body;
				});
		}
	},
	methods: {
		/**
		 * 保存処理.
		 * 現在のTODOの内容を元にTODO保存APIを呼び出す。
		 */
		save: function() {

			var exec = null;

			if (this.todo.id == null) {
				
				exec = window.superagent.post(this.url.base);
			} else {

				exec = window.superagent.put(this.url.base + "/" + this.todo.id);
			}

			var that = this;
			exec
				.type("form")
				.set(CommonProps.csrfHeaderName, CommonProps.csrfToken)
				.send(this.todo)
				.end(function(err, res) {

					var forwardId = that.todo.id || res.body.id;

					if (res.status == 201 || res.status == 204) {

						that._forwardDetails(forwardId);
					}

					if (res.status == 400) {

						that.$refs.registerErrors.add(res.body.errors);
					}

					if (res.status == 409) {

						that.$refs.registerErrors.conflict("TODOの状態が最新ではありません。", "リフレッシュ");
					}
				});
		},

		/**
		 * 詳細画面に遷移する.
		 * @param {Number} forwardId 遷移させるTODOのID
		 */
		_forwardDetails: function(forwardId) {

			this.$router.go({
				name: "detail",
				params : {
					id: forwardId
				}
			});
		}
	}
});
