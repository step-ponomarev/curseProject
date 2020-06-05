<script>
    import Checkbox from '@smui/checkbox';
    import FormField from '@smui/form-field';
    import Button, {Group, GroupItem, Label, Icon} from '@smui/button';
    import Textfield, {Input, Textarea} from '@smui/textfield';
    import { DOMAIN } from '../util.js';

    let username = '';
    let password = '';

    let roles = Array();

    async function register() {
        const user = {
            username: username,
            password: password,
            roles: roles
        }

        const response = await fetch(DOMAIN + '/user', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: await JSON.stringify(user)
        });

        if (response.ok) {
            console.log(`Registred User ${await response.json().username}`);
        }
    }

</script>

<div class="container">
    <form>
        <div class="title">
            Registration
        </div>

        <Textfield required bind:value={username}
                   label="Username"
                   type="text"
        />

        <Textfield required bind:value={password}
                   label="Password"
                   type="password"

        />

        <FormField>
            <span slot="label">Admin</span>
            <Checkbox bind:group={roles} value="ADMIN"/>
        </FormField>

        <FormField>
            <span slot="label">User</span>
            <Checkbox bind:group={roles} value="USER"/>
        </FormField>
        <Button on:click={register}><Label>Зарегистрироваться</Label></Button>
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
        justify-content: left;
        gap: 0.3em;
        align-items: start;
        box-sizing: border-box;
        margin-bottom: 8em;
        box-shadow: 0px 0px 3px 3px rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        padding: 1em;
        padding-left: 2em;
        padding-right: 2em;
    }

    .title {
        font-size: 24px;
        margin-bottom: 0.5em;
    }
</style>