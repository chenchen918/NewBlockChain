var app = new Vue({
    el: '#app',
    data: {
        Transactions: [],

    },
    methods: {
        getTransaction() {
            axios.get("/transaction/getTransaction")
                .then(res => {
                    console.log(res)
                    this.Transactions = res.data;
                })
                .catch(err => {
                    console.error(err);
                })
        }

    },
    mounted() {
        this.getTransaction();
    },
})