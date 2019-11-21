var app = new Vue({
    el: '#app',
    data: {
        address: "",
        Byaddress: {},

    },
    methods: {
        getInfoByAddress() {
            axios.get("/address/getInfoByAddress", { params: { address: this.address } })
                .then(res => {
                    console.log(res)
                    this.Byaddress = res.data;
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
        this.getInfoByAddress();
    },
})