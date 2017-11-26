//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法)
var seckill = {

    //封装秒杀相关ajax的url
    URL: {

    },

    //验证手机号
    validatePhone : function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else {
            return false;
        }
    },

    //详情页秒杀逻辑
    detail:{
        //详情页初始化
        init : function (params) {
            //手机验证和登录，计时交互
            //在cookie中查找手机
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];

            //验证手机号
            if (!seckill.validatePhone(killPhone)){
                //绑定phone
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show : true,
                    backdrop :false,
                    keyborad : false
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log('inputPhone='+inputPhone);
                    if(seckill.validatePhone(inputPhone)){
                        $.cookie('killPhone',inputPhone,{expires : 7,path : '/seckill'});
                        window.location.reload();
                    }else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });


        }
    },



}