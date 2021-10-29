#fastdfs文件服务器，统一上传/下载接口。
#prot=10999

###方式一：form表单提交文件-上传
<html>
<body>
<form action="http://192.168.1.46:10999/api/v1/fastdfs/pc/upload" method="post" enctype="multipart/form-data">
  <input type="file" name="file" />
  <!-- <input type="file" name="file" /> 批量多个-->
  <input type="submit" value="submit" />
</form>
</body>
</html>

###方式二：ajax异步提交文件-上传
<input type='file' name='file'/>
var formData = new FormData();
formData.append("file", document.getElementById("file").files[0]);   
$.ajax({
	url: "http://192.168.1.46:10999/api/v1/fastdfs/pc/upload",
	type: "POST",
	data: formData,
	contentType: false,
	processData: false,
	success: function (data) {
		alert("上传成功！");
	},
	error: function () {
		alert("上传失败！");
	}
});