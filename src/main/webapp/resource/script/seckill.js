//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法)
var seckill = {

    //封装秒杀相关ajax的url
    URL: {
        now : function () {
          return  '/seckill/time/now' ;
        },
        exposer : function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        execution : function (seckillId , md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }
    },

    handleSeckillKill : function (seckillId ,node) {
        //处理秒杀逻辑
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId),{},function (result) {

            if (result && result['success']){
                var exposer = result['data'];
                if (exposer['exposed']){
                    //开始秒杀
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    console.log('killUrl:'+killUrl);
                    //绑定一次点击
                    $('#killBtn').one('click',function () {
                        //点击一次 禁用按钮
                        $(this).addClass('disabled');
                        //发送请求
                        $.post(killUrl,{},function (result) {
                            if (result && result['success']){
                                var killresult = result['data'];
                                var state = killresult['state'];
                                var stateInfo = killresult['stateInfo'];
                                //显示秒杀结果
                                //node.html('<span class="label label-success">'+stateInfo+'</span>');
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }
                        });
                    });
                    node.show();
                }else {
                    var now = exposer['now'];
                    var start =exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId,now,start,end);
                }
            }else{

            }
        });
    },

    //验证手机号
    validatePhone : function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else {
            return false;
        }
    },
    countdown : function (seckillId,nowTime,startTime,endTime) {
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime){
            seckillBox.html('秒杀结束！');
        }else if (nowTime < startTime){
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function (event) {
                var format =event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function () {
                //获取秒杀地址，控制显示逻辑，执行秒杀
                seckill.handleSeckillKill(seckillId , seckillBox);

            });
        }else {
            //秒杀开始
            seckill.handleSeckillKill(seckillId , seckillBox);
        }
    },

    //详情页秒杀逻辑
    detail:{
        //详情页初始化
        init : function (params) {
            //手机验证和登录，计时交互
            //在cookie中查找手机
            var killPhone = $.cookie('killPhone');

            //验证手机号
            if (!seckill.validatePhone(killPhone)){
                //绑定phone
                var killPhoneModal = $('#killPhoneModal');

                killPhoneModal.modal({
                    show: true,//显示弹出层
                    backdrop: false,//禁止位置关闭《《《《《《----这里把static改为false
                    keyboard: false//关闭键盘事件

                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log('inputPhone='+inputPhone);
                    if(seckill.validatePhone(inputPhone)){
                        $.cookie('killPhone',inputPhone, {expires : 7,path : '/seckill'});
                        window.location.reload();
                    }else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
        }

            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
        $.get(seckill.URL.now(),{},function (result) {
            if (result && result['success']){
                var nowTime =result['data'];
                //判断时间,计时交互
               seckill.countdown(seckillId,nowTime,startTime,endTime);
            }else{
                console.log("result:"+result);
            }
        })
    }
 }
}