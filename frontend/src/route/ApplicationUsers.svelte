<script>
    import {onMount} from 'svelte';

    let users = Array();

    onMount(() => {
        fetch('/user/all')
                .then(response => response.json())
                .then(json => {
                    users = Array.from(json);
                });
    });
</script>


<div class="container">
    <div class="row title">
        <div class="id-width">Id</div>
        <div class="username-width">Username</div>
        <div class="roles-width">Roles</div>
    </div>

    {#each users.slice() as user}
        <div class="row bordered">
            <div class="id-width">{user.id}</div>
            <div class="username-width">{user.username}</div>
            <div class="roles-width">{user.roles}</div>
        </div>
    {/each}
</div>

<style>
    .container {

        box-sizing: border-box;
        display: grid;
        margin-right: auto;
        margin-left: auto;
        gap: 0.5em;
        min-width: 58em;
    }

    .row {
        display: flex;
        width: 100%;
        box-sizing: border-box;
    }

    .bordered {
        padding-top: 0.5em;
        padding-bottom: 0.5em;
        border-bottom: 1px dotted #bfbfbf;
        border-top: 1px dotted #bfbfbf;
    }

    .id-width {
        width: 30%;
    }

    .username-width {
        width: 30%;
    }

    .roles-width {
        width: 40%;
    }
</style>