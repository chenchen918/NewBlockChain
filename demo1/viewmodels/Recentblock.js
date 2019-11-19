var app = new Vue({
    el: '#app',
    data: {
        ListRecentBlock: [],
        type: [{ id: 1, name: "块" }, { id: 2, name: "交易" }],

    },
    methods: {
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