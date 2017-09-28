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
        
        HTed.prepareQueryParameter = function (obj, options) {
            obj = obj || {};
            if (options) {
                obj.page = options.page;
                obj.pagesize = options.pageSize;
                if (options.sort && options.sort.length > 0) {
                    obj.sortname = options.sort[0].field;
                    obj.sortorder = options.sort[0].dir;
                }
            }
            for (var k in obj) {
                if (obj[k] === '' || obj[k] === null || obj[k] === undefined)
                    delete obj[k]
                if (obj[k] instanceof Date) {
                    obj[k] = obj[k].toJSON()
                }
            }
            return obj;
        };
        
        HTed.prepareSubmitParameter = function (options, type) {
            var datas = options.models;
            $.each(datas, function (i, r) {
                if (type == 'create')
                    r['__status'] = 'add';
                else if (type == 'update')
                    r['__status'] = 'update';
                else if (type == 'destroy')
                    r['__status'] = 'delete';
            });
            return datas;
        };
  }
})(jQuery)