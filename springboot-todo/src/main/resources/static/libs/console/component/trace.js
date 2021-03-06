/**
 * Trace
 */
var Trace = Vue.extend({
	template: "#trace",
	data: function() {

		return {
			trace: [],
		}
	},
	ready: function() {

		var that = this;
		window.superagent
			.get("/monitor/httptrace")
			.accept('application/json')
			.end(function(err, res) {

				that.$set("trace", res.body.traces);
			});
	}
});
