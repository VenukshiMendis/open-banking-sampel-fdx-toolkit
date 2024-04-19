// This script will prompt concurrent session handling
// to one of the given roles
// If the user has any of the below roles, concurrent session handling will be prompted
// and it will either kill sessions or abort login based on number of active concurrent user sessions
var rolesToStepUp = ['admin', 'manager'];
var maxSessionCount = 1;

var onLoginRequest = function(context) {
        executeStep(1, {
                onSuccess: function (context) {
                        // Extracting authenticated subject from the first step
                        var user = context.currentKnownSubject;
                        // Checking if the user is assigned to one of the given roles
                        var hasRole = hasAnyOfTheRoles(user, rolesToStepUp);

                        if (hasRole) {
                                Log.info(user.username + ' Has one of Roles: ' + rolesToStepUp.toString());
                                executeStep(2, {
                                        authenticatorParams: {
                                                local: {
                                                        SessionExecutor: {
                                                                MaxSessionCount: '1'
                                                        }
                                                }
                                        }
                                }, {});
                        }
                }
        });
};