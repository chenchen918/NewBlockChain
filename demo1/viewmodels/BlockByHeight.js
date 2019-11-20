var app = new Vue({
    el: '#app',
    data: {
        ListByheight: {},
        height: "",
        listTransaction: [],
        total: 0,
        pageSize: 0,
        TransactionDetailJsonss: []
    },
    methods: {
        currentChange(pageNum) {
            this.getListTransactionByheight(pageNum);
        },
        getListByheight() {
            axios.get("/Block/getBlockByHeight", { params: { height: this.height } })
                .then(res => {
                    this.ListByheight = res.data;
                })
                .catch(err => {
                    console.error(err);
                })
        },
        getListTransactionByheight(pageNum) {
            axios.get("/transaction/getBlockByHeightPage", { params: { height: this.height, pageNum: pageNum } })
                .then(res => {
                    console.log(res)
                    this.listTransaction = res.data.list;
                    this.total = res.data.total;
                    this.pageSize = res.data.pageSize;
                    this.TransactionDetailJsonss = this.listTransaction.TransactionDetailJsons;
                    console.log(this.TransactionDetailJsons)
                })
                .catch(err => {
                    console.error(err);
                })
        }
    },
    mounted() {
        var url = new URL(location.href);
        var height = url.searchParams.get("height");
        this.height = height;
        this.getListByheight();
        this.getListTransactionByheight();
    },
})