var app = new Vue({
    el: '#app',
    data: {

        BlockHash: {},
        Transactions: [],
        Transactionss: [],


    },
    methods: {
        timestampToTime(timestamp) {
            var date = new Date(timestamp * 1000); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
            // let Y = date.getFullYear() + '-';
            // let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
            // let D = date.getDate() + ' ';
            // let h = date.getHours() + ':';
            // let m = date.getMinutes() + ':';
            // let s = date.getSeconds();
            // return Y+this.padLeftZero(M)+this.padLeftZero(D)+this.padLeftZero(h)+this.padLeftZero(m)+this.padLeftZero(s);
            let y = date.getFullYear();
            let MM = date.getMonth() + 1;
            MM = MM < 10 ? ('0' + MM) : MM;
            let d = date.getDate();
            d = d < 10 ? ('0' + d) : d;
            let h = date.getHours();
            h = h < 10 ? ('0' + h) : h;
            let m = date.getMinutes();
            m = m < 10 ? ('0' + m) : m;
            let s = date.getSeconds();
            s = s < 10 ? ('0' + s) : s;
            return y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s;
        },
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