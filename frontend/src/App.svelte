<script>
    import {MARKS, GROUPS, SUBJECTS, PEOPLE, USER} from './store.js';
    import Nav from './component/Nav.svelte';
    import EditMarks from './route/EditMarks.svelte';
    import Marks from './route/Marks.svelte';
    import NeedAuth from './route/NeedAuth.svelte';
    import People from './route/Person.svelte';
    import Group from './route/Group.svelte';
    import Subject from './route/Subject.svelte';
    import Authorized from './route/Authorized.svelte';
    import InvalidRoot from './route/InvalidRoot.svelte';
    import LoginPage from './route/LoginPage.svelte';
    import {Router, Link, Route} from "svelte-routing";
    import {onMount} from 'svelte';
    import {GET, DOMAIN} from './util.js';
    import Registration from './route/Registration.svelte';
    import ApplicationUsers from "./route/ApplicationUsers.svelte";

    let DATA_IS_READY = false;

    onMount(async () => {
        await fetch(DOMAIN + '/user').then(user => {
            user.json()
                    .then(result => {
                        USER.set(result);

                        GET(DOMAIN + '/mark').then(marks => {
                            $MARKS = Array.from(marks);
                        });
                        GET(DOMAIN + '/group').then(groups => {
                            $GROUPS = Array.from(groups);
                        });
                        GET(DOMAIN + '/person').then(people => {
                            $PEOPLE = Array.from(people);
                        });
                        GET(DOMAIN + '/subject').then(subjects => {
                            $SUBJECTS = Array.from(subjects);
                        });
                    }).catch(() => USER.set(null));
        }).then(() => DATA_IS_READY = true);
    });
</script>

<Router>
    <header>
        <Nav/>
    </header>
    {#if DATA_IS_READY}
        <main>
            {#if $USER !== null}
                <Route path="/" component={Authorized}/>
                <Route path="editPeople" component={People}/>
                <Route path="editSubjects" component={Subject}/>
                <Route path="editGroups" component={Group}/>
                <Route path="marks" component={Marks}/>
                <Route path="editMarks" component={EditMarks}/>
                {#if $USER.roles.find(role => role === 'ADMIN') !== undefined}
                    <Route path="application_users" component={ApplicationUsers}/>
                {/if}
            {:else}
                <Route path="registration" component={Registration}/>
                <Route path="login" component={LoginPage}/>
                <Route path="/" component={NeedAuth}/>
            {/if}

            <Route path="" component={InvalidRoot}></Route>
        </main>
    {/if}

</Router>


<style>

    header {
        width: 100%;
        height: 50px;
        box-sizing: border-box;
    }

    main {
        width: 100%;
        height: calc(100% - 50px);
        box-sizing: border-box;
        padding: 1em;
    }
</style>