var app = new Vue({
    el: '#app',
    data: {
        address: "",
        Byaddress: {},

        listTransaction: [],
        total: 0,
        pageSize: 0,
        TransactionDetailJsonss: []

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
        currentChange(pageNum) {
            this.getBlockByAddressPage(pageNum);
        },
        getInfoByAddress() {
            axios.get("/address/getInfoByAddress", { params: { address: this.address } })
                .then(res => {
                    console.log(res)
                    this.Byaddress = res.data;
                })
                .catch(err => {
                    console.error(err);
                })
        },
        getBlockByAddressPage(pageNum) {
            axios.get("/transaction/getBlockByAddressPage", { params: { address: this.address, pageNum: pageNum } })
                .then(res => {
                    console.log(res)
                    this.listTransaction = res.data.list;
                    this.total = res.data.total;
                    this.pageSize = res.data.pageSize;
                    console.log(res.data.list.txid)
                    this.listTransaction.forEach(element => {
                        this.TransactionDetailJsonss = element.TransactionDetailJsons;
                    });
                })
                .catch(err => {
                    console.error(err);
                })
        }
    },
    mounted() {
        var url = new URL(location.href);
        var address = url.searchParams.get("address");



        this.address = address;
        var qrcode = new QRCode("AddressQRCode", {
            text: this.address,
            width: 128,
            height: 128,
            colorDark: "#000000",
            colorLight: "#ffffff",
            correctLevel: QRCode.CorrectLevel.H
        });
        this.getInfoByAddress();
        this.getBlockByAddressPage();

    },
})