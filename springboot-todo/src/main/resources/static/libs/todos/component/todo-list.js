/**
 * TODO一覧コンポーネント.
 */
var TodoList = Vue.extend({
	template: "#todo-list",
	data: function() {

		return {
			/** TODO一覧. */
			details: [],

			/** TODOの統計. */
			statistics: null
		}
	},
	ready: function() {

		var that = this;
		window.superagent
			.get("/api/todos")
			.end(function(err, res) {

				that.$set("details",  res.body.details);
				that.$set("statistics", res.body.statistics);
			});
	}
});
