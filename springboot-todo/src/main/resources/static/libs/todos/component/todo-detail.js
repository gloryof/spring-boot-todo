/**
 * TODOの詳細コンポーネント.
 */
var TodoDetail = Vue.extend({
	template: "#todo-detail",
	data: function() {

		return {
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

				/** 完了フラグ. */
				completed: false
			}
		}
	},
	ready: function() {

		var id = this.$route.params.id;

		var that = this;
		window.superagent
			.get("/api/todos/" + id)
			.type("form")
			.end(function(err, res) {
				that.todo = res.body;
			});
	}
});
