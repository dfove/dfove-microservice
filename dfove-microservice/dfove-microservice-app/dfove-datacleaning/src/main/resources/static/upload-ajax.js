//上传文件
function uploadFile(){
	//获取上传文件对象
	var uploadFile =document.getElementById("identityImage_file").files[0];
	var formdata = new FormData();
	formdata.append('file', uploadFile);
	
	//上传地址
	var paramUrl="http://192.168.1.205:8808/fastdfs/dfove/v1/fastdfs/pc/upload";
	//上传参数
	var paramData=formdata;
	//ajax请求
	var result = ajax_post_asyncFalse_file(paramUrl, paramData);
	if (result == null) {
		return false;
	}
	//msg
	alert(JSON.stringify(result));
}

//ajax-upload
function ajax_post_asyncFalse_file(url,data){
	var result=null;
    $.ajax({
        async: false,//同步
        cache : false,
        url: url,
        data:data,
        dataType: 'json',
        type: 'POST',
        contentType: false,
        processData: false,
        beforeSend: function (XMLHttpRequest) {
			var authorization='xXXXXXX';
        	XMLHttpRequest.setRequestHeader("Authorization","Bearer "+authorization);
        },
        success: function(res) {
        	result=res;
        },
        error: function(res) {
        	
        },
        xhr: function() {
           
        }
    })
    .done(function(res) {
    	//success
    })
    .fail(function(res) {
    	
    })
    .always(function() {
    	
    });
    return result;
}



