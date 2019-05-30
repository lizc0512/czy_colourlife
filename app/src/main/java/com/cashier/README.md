##收银台
###主要功能

1.支付,调用收银台支付功能

	Cashier mCashier = new Cashier(this);
	String orderSn = "xxxxxxxx";
	mCashier.pay(orderSn, 
					new Cashier.CashierListener() {
                @Override
                public void OnPaySucceed() {
                    Log.e("hahahah");
                }

                @Override
                public void OnPayFailed() {

                }

                @Override
                public void OnPayCanceled() {

                }
            });
	
	            
2.密码管理
