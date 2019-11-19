var app = new Vue({
    el: '#app',
    data: {
        ListRecentBlock: [],

    },
    methods: {
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