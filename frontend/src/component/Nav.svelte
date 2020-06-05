<script>
    import {USER, MARKS, PEOPLE, SUBJECTS} from '../store.js';
    import {link} from 'svelte-routing';
    import {onMount} from "svelte";

    let currentRoot = document.location.pathname.substr(document.location.pathname.lastIndexOf('/') + 1);

    let marksExist;
    let canCreateMark;

    $: {
        marksExist = $MARKS.length !== 0;

        canCreateMark = $PEOPLE.filter(person => person.role == "TEACHER").length !== 0 &&
                $PEOPLE.filter(person => person.role == "STUDENT").length !== 0 &&
                $SUBJECTS.length !== 0
    }

    function setRoot() {
        currentRoot = this.id
    }
</script>


<div class="container">
    {#if $USER !== null}
        <a id="editPeople" class:active={currentRoot === 'editPeople'} on:click={setRoot} href="/editPeople" use:link>
            People </a>
        <a id="editSubjects" class:active={currentRoot === 'editSubjects'} on:click={setRoot} href="/editSubjects"
           use:link>
            Subject
        </a>
        <a id="editGroups" class:active={currentRoot === 'editGroups'} on:click={setRoot} href="/editGroups" use:link>
            Group </a>
        {#if canCreateMark}
            <a id="marks" class:active={currentRoot === 'marks'} on:click={setRoot} href="/marks" use:link> Marks </a>
        {/if}
        {#if marksExist}
            <a id="editMarks" class:active={currentRoot === 'editMarks'} on:click={setRoot} href="/editMarks" use:link>
                Edit
                Marks</a>
        {/if}

        {#if $USER.roles.find(role => role === 'ADMIN') !== undefined}
            <a id="application_users" class:active={currentRoot === 'application_users'} on:click={setRoot}
               href="/application_users" use:link>
                Application users </a>
        {/if}
    {/if}

    <div class="{$USER == null ? `left` : `right`}">
        {#if $USER == null}
            <a id="registration" class:active={currentRoot === 'registration'} on:click={setRoot} href="/registration"
               use:link>registration </a>

            <a id="login" href="/login" class="logoutLink {currentRoot === 'login' ? 'active' : ''}"
               on:click={setRoot} use:link> login </a>
        {:else}
            <a id="logout" href="/logout" class="logoutLink"> logout </a>

        {/if}
    </div>

</div>


<style>
    .container {
        padding: 1em;
        display: flex;
        grid-auto-flow: column;
        justify-content: flex-start;
        align-content: center;
        gap: 1em;
        width: 100%;
        height: 100%;
        box-sizing: border-box;
        background-color: #e88936;
    }

    .right {
        margin-left: auto;
        margin-right: 0;
    }

    .left {
        margin-right: auto;
        margin-left: 0;
    }

    a {
        font-size: 18px;
        margin-right: 1em;
        text-decoration: none;
        color: #eeeeee;
    }

    a:hover {
        color: white;
    }

    .active {
        color: white;
    }

    .logoutLink {
        font-size: 16px;
    }
</style>
