import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { connect } from 'react-redux'
import PropTypes from "prop-types"

class Landing extends Component {

    componentDidMount() {
        if (this.props.security.validToken) {
            this.props.history.push("/dashboard");
        }
    }

    render() {
        return (
            <div className="landing">
                <div className="light-overlay landing-inner text-dark">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12 text-center">
                                <h1 className="display-3 mb-4">Personal Project Management Tool</h1>
                                <p className="lead">
                                    Create your account to join active projects or start your own
                            </p>
                                <hr />
                                <Link to="/register" className="btn btn-lg btn-primary mr-2" >
                                    Sign Up
                        </Link>
                                <Link to="/login" className="btn btn-lg btn-secondary mr-2">
                                    Login
                            </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

Landing.propTypes = {
    security: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    security: state.security
})

export default connect(mapStateToProps)(Landing);
