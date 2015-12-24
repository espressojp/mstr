/**
 * Show ROW TITLE and ROW HEADER (attribute header and elements)
 */
(function () {
    // Define this code as a plugin in the mstrmojo object
    if (!mstrmojo.plugins.D3Table) {
        mstrmojo.plugins.D3Table = {};
    }
    // All mojo visualization require the CustomVisBase library to render
    mstrmojo.requiresCls("mstrmojo.CustomVisBase",
                         "mstrmojo.models.template.DataInterface"
                        );
    // Declare the visualization object
    mstrmojo.plugins.D3Table.D3Table = mstrmojo.declare(
        // Declare that this code extends CustomVisBase
        mstrmojo.CustomVisBase,
        null,
        {
            // Define the JavaScript class that renders your visualization as mstrmojo.plugins.{plugin name}.{js file name}
            scriptClass: 'mstrmojo.plugins.D3Table.D3Table',
            externalLibraries: [{url: "//d3js.org/d3.v3.min.js"}],
            plot:function(){
                console.log("D3Table starting...");
                debugger;
                
                var table = d3.select(this.domNode).append('table');
                var thead = table.append('thead'); 
                var tbody = table.append('tbody');
                                
                // data from dataset
                var gridData = new mstrmojo.models.template.DataInterface(this.model.data);
                var rowTitles = gridData.getRowTitles();
                
                var titles = function(rowTitles){
                    var titles = [];
                    for(i=0; i< rowTitles.size(); i++){
                        titles.push(rowTitles.getTitle(i).getName());
                    }
                    return titles;
                }
                
                
                var headers = function(gridData){
                    var headers = [];
                    for(j=0; j< gridData.getTotalRows(); j++){
                        headers[j] = [];
                        for(i=0; i< rowTitles.size(); i++){
                            headers[j][i] = gridData.getRowHeaders(j).getHeader(i).getName();
                        }
                    }
                    return headers;
                }

                thead.append('tr')
                     .selectAll('th')
                     .data(titles(rowTitles))
                     .enter()
                     .append('th')
                     .text(function(d){return d});
                
                tbody.selectAll('tr')
                     .data(headers(gridData))
                     .enter()
                     .append('tr')
                     .selectAll('td')
                     .data(function(row){return d3.entries(row);})
                     .enter()
                     .append('td')
                     .text(function(d){return d.value;});
                

            }})
}());