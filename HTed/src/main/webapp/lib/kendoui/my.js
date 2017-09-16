!(function ($) {  
 if (!window.HTed) {
	 HTed = {
        };
	function resizeGrid(grid_id) {
            var g = $(grid_id);
            var grid_container = g.parent();
            var extra = parseInt(g.css('marginTop')) || 0 + parseInt(g.css('marginBottom')) || 0;
            var avaHeight = $(window).height() - grid_container.offset().top - 10 - extra;
            grid_container.outerHeight(avaHeight);
            if (!g.data('kendoGrid'))
                return
            g.data('kendoGrid').resize();
        }

        HTed.autoResizeGrid = function (grid_id) {
            resizeGrid(grid_id);
            $(window).resize(function () {
                resizeGrid(grid_id);
            });
        }
  }
})(jQuery)