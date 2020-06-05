<script>

</script>

<style>
    .container {
        display: grid;
        justify-content: center;
        align-content: center;
        width: 100%;
        height: 100%;
        box-sizing: border-box;
    }

    .greeting {
        display: grid;
        justify-content: center;
        padding: 1em;
        gap: 1em;
    }

    .title {
        justify-self: center;
        width: max-content;
        font-size: 22px;
    }

    .image {
        width: 500px;
        height: auto;

    }

    .image > img {
        width: 100%;
        height: 100%;
        box-sizing: border-box;
    }
</style>

<div class="container">

    <div class="greeting">
        <div class="title">
            Такой страницы не существует
        </div>

        <div class="image">
            <img src="../img/angry.jpg" alt="">
        </div>
    </div>


</div>