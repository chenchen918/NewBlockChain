var app = new Vue({
    el: '#app',
    data: {
        ListRecentBlock: [],
        type: [{ id: 1, name: "块" }, { id: 2, name: "交易" }],
        keyword: ""

    },
    methods: {
        handleSearchClick() {
            console.log('search click');

            if (!isNaN(this.keyword)) {
                console.log('最近块信息');
                this.getListRecentBlock();
                return;
            }

            if (this.keyword.length < 64) {
                console.log('跳转地址页面');
                location.href = 'GetByaddress?address=' + this.keyword;
                return;
            }

            if (this.keyword.startsWith('00000')) {
                console.log('根据hash查询块信息');
                location.href = 'BlockShow?blockhash=' + this.keyword;
                return;
            } else {
                console.log('交易信息');
                location.href = 'Transaction?txid=' + this.keyword;
                return;
            }
        },
        ToTransition() {

            window.location.href = "http:/Transaction";
        },
        getListRecentBlock() {
            axios.get("/Block/getRecentblock")
                .then(res => {
                    console.log(res)
                    this.ListRecentBlock = res.data;
                })
                .catch(err => {
                    console.error(err);
                })
        }
    },
    mounted() {
        this.getListRecentBlock();
    },
})