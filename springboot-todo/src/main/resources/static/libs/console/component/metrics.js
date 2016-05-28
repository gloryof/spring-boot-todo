/**
 * Metrics
 */
var Metrics = Vue.extend({
	template: "#metrics",
	data: function() {

		return {
			metrics: {
				uptime: 0,
				instance: {
					uptime: 0
				},
				processors: 0,
				systemload: {
					average: 0
				},
				mem: {
					count: 0,
					free: 0
				},
				classes: {
					count: 0,
					loaded: 0,
					unloaded: 0
				},
				heap: {
					count: 0,
					committed: 0,
					init: 0,
					used: 0
				},
				nonheap: {
					count: 0,
					committed: 0,
					init: 0,
					used: 0
				},
				threads: {
					count: 0,
					peak: 0,
					daemon: 0
				},
				httpsessions: {
					max: 0,
					active: 0
				},
				counter: {},
				gauge: {}
			},
		}
	},
	ready: function() {

		var that = this;
		window.superagent
			.get("/monitor/metrics")
			.end(function(err, res) {

				that.metrics.uptime = res.body.uptime;
				that.metrics.instance.uptime = res.body["instance.uptime"];
				that.metrics.processors = res.body.processors;
				that.metrics.systemload.average = res.body["systemload.average"];
				that.metrics.mem.count = res.body.mem;
				that.metrics.mem.free = res.body["mem.free"];
				that.metrics.classes.count = res.body.classes;
				that.metrics.classes.loaded = res.body["classes.loaded"];
				that.metrics.classes.unloaded = res.body["classes.unloaded"];
				that.metrics.heap.count = res.body.heap;
				that.metrics.heap.committed = res.body["heap.committed"];
				that.metrics.heap.init = res.body["heap.init"];
				that.metrics.heap.used = res.body["heap.used"]
				that.metrics.nonheap.count = res.body.nonheap;
				that.metrics.nonheap.committed = res.body["nonheap.committed"];
				that.metrics.nonheap.init = res.body["nonheap.init"];
				that.metrics.nonheap.used = res.body["nonheap.used"];
				that.metrics.threads.count = res.body.threads;
				that.metrics.threads.peak = res.body["threads.peak"];
				that.metrics.threads.daemon = res.body["threads.daemon"];
				that.metrics.httpsessions.max = res.body["httpsessions.max"];
				that.metrics.httpsessions.active = res.body["httpsessions.active"];

				var counter = {};
				var gauge = {};
				for (var k in res.body) {

					var splited = k.split(".")[0];
					if (splited == "counter") {

						counter[k] = res.body[k];
					}
					if (splited == "gauge") {

						gauge[k] = res.body[k];
					}
				}

				that.$set("metrics.counter", counter);
				that.$set("metrics.gauge", gauge);
			});
	}
});
