var app = new Vue({
    el: '#app',
    data: {

        BlockHash: {},
        Transactions: [],
        Transactionss: [],


    },
    methods: {
        getBlickByBlockHash(blockhash) {
            axios.get("/Block/getBlockByHash", { params: { blockhash: blockhash } })
                .then(res => {
                    console.log(res)
                    this.BlockHash = res.data;
                    this.Transactions = res.data.transactions;
                    this.Transactionss = res.data.transactions.transactions;
                    console.log(res.data.transactions.transactions)
                })
                .catch(err => {
                    console.error(err);
                })
        }
    },
    mounted() {
        var url = new URL(location.href);
        var blockhash = url.searchParams.get("blockhash");
        this.getBlickByBlockHash(blockhash);

    },
})