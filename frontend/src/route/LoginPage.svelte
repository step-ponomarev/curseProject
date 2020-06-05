<script>
    import Button, {Group, GroupItem, Label, Icon} from '@smui/button';
    import Textfield, {Input, Textarea} from '@smui/textfield';
    import {DOMAIN} from '../util.js';

    let username = '';
    let password = '';

    let form;

</script>

<div class="container">
    <form bind:this={form}
          action="/login"
          method="post"
    >
        <div class="title">
            Login
        </div>

        <div class="textField">
            <label for="username">
                Username
            </label>
            <input type="text"
                   name="username"
                   id="username"/>
        </div>

        <div class="textField">
            <label for="password">
                Password
            </label>
            <input type="password"
                   name="password"
                   id="password"/>
        </div>

        <div class="button"><Button on:click={() => form.submit()}><Label>Войти</Label></Button></div>
    </form>
</div>

<style>
    .container {
        width: 100%;
        height: 100%;
        box-sizing: border-box;
        display: grid;
        justify-content: center;
        align-content: center;
    }

    form {
        display: grid;
        justify-content: center;
        gap: 0.5em;
        align-items: start;
        box-sizing: border-box;
        margin-bottom: 8em;
        box-shadow: 0px 0px 3px 3px rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        padding: 1em;
        padding-left: 2em;
        padding-right: 2em;
    }

    .textField {
        display: grid;
        justify-content: left;
        align-content: start;
        gap: 0.5em;
    }

    .title {
        font-size: 24px;
        margin-bottom: 0.5em;
    }


    input {
        cursor: pointer;
    }

    input[type="text"]:focus, input[type="password"]:focus {
        border-bottom-color: #e88936;
    }

    input[type="text"]:hover, input[type="password"]:hover {
        border-bottom-color: #e88936;
    }

    .button {
        justify-self: center;
    }
</style>