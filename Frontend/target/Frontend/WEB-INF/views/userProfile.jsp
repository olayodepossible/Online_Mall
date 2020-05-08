<div class="content">

    <div class="container">

        <div class="row">

            <div class="col-md-offset-3 col-md-6">

                <div class="panel panel-primary">

                    <div class="panel-heading">
                        <h4>User Profile</h4>
                    </div>

                    <div class="panel-body">
                        <form  class="form-horizontal"
                            modelAttribute ="userModel"  id="loginForm" >
                            <div class="form-group">
                                <label class="col-md-4 control-label">Email: </label>
                                <div class="col-md-8">
                                    <input type="text" name="username" value="${userModel.getFullName()}" class="form-control"/>
                                </div>
                            </div>

                            <div>
                                <label class="col-md-4 control-label">Password: </label>
                                <div class="col-md-8">
                                    <input type="text" name="firstname" value="${userModel.getPassword()}" class="form-control"/>
                                </div>

                            </div>
                        </form>

                    </div>

                </div>

            </div>

        </div>

    </div>


</div>




