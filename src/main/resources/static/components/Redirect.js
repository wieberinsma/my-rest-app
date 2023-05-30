export default function Redirect(json)
{
    fetch(json.redirectUrl, {
        method: 'get'
    })
        .then(response =>
        {
            if (response.status === 200)
            {
                return Promise.resolve();
            }
            else
            {
                return Promise.reject("Insufficient rights.")
            }
        })

    return (
        <div className="Private-container">
            <h3 className="Private-title">Welcome, you have reached a private resource!</h3>
        </div>
    )
}
