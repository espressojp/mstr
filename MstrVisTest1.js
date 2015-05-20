/**
 * TreemapのVIダッシュボードへのインテグレーション
 */
(function () {
    // Define this code as a plugin in the mstrmojo object
    if (!mstrmojo.plugins.MstrVisTest1) {
        mstrmojo.plugins.MstrVisTest1 = {};
    }
    // All mojo visualization require the CustomVisBase library to render
    mstrmojo.requiresCls("mstrmojo.CustomVisBase",
                         "mstrmojo.models.template.DataInterface"
                        );
    // Declare the visualization object
    mstrmojo.plugins.MstrVisTest1.MstrVisTest1 = mstrmojo.declare(
        // Declare that this code extends CustomVisBase
        mstrmojo.CustomVisBase,
        null,
        {
            // Define the JavaScript class that renders your visualization as mstrmojo.plugins.{plugin name}.{js file name}
            scriptClass: 'mstrmojo.plugins.MstrVisTest1.MstrVisTest1',
            externalLibraries: [{url: "http://d3js.org/d3.v3.min.js"}],
            plot: function () {
                //...ADD YOUR JS CODE...
 	            console.log("ZoomTree:starting...");

                
                var $D1 = mstrmojo.models.template.DataInterface,
                    normalizedModel = (new $D1(this.model.data)).getRawData($D1.ENUM_RAW_DATA_FORMAT.DATA_FORMAT_ROWS);
                
 	            console.log(normalizedModel);

                
                var width = parseInt(this.width, 10),
                    height = parseInt(this.height, 10),
                    color = d3.scale.category20c(),
                    transitionDuration = 750;
                
                var svg = d3.select(this.domNode).select("svg");

                if (svg.empty()) {
                    svg = d3.select(this.domNode).append("svg")
                        .attr("class", "ZoomTree");
                }
                
                svg.attr("width", width)
                    .attr("height", height);
                
                var treemap = d3.layout.treemap()
                    .size([width, height - 100])
                    .value(function(d) { return d.formattedValue; })
                    .children(function(d) { return d.children; })
                
                
                var nodes = treemap.nodes(normalizedModel);
	            console.log(nodes);
    
	            var element = svg.selectAll("g")
	                .data(nodes)
	                .enter().append("g") 
	                .attr("class","cell");
    
    
                    element.append("rect")
                    .call(rect);
        
                    element.append("text")
                    .call(text);
                
                
                function rect(rect) {
                    rect.attr("x", function(d){ return d.x; })
                        .attr("y", function(d){ return d.y; })
                        .attr("width", function(d){ return d.dx; })
                        .attr("height", function(d){ return d.dy; })
                        .attr("fill",function(d){ return d.children ? null : color(d.parent.name); })
                        .attr("stroke", "blue")
                        .attr("stroke-width",2);
                }
                
                function text(text) {
                    text.attr("x", function(d){ return d.x + (d.dx/2); })
                        .attr("y", function(d){ return d.y + (d.dy/2); })
                        .attr("text-anchor","middle")
                        .text(function(d){ return d.children ? "" : d.name; })
                        .attr("stroke", "black");
                }
                
                
            }
        });
})();