<?php

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use AppBundle\Entity\Commodity;
use AppBundle\Entity\Order;

class ChargeController extends Controller
{
    /**
     * @Route("/charge", name="charge")
     */
    public function indexAction(Request $request)
    {
    	  $repository = $this->getDoctrine()->getRepository(Commodity::class);
        $items = $repository->findAll();
        $shopping_cart = $_SESSION['shopping_cart'];
        
        # regenerate the item array, use id as key
        $item_arr = array(); 
        foreach ($items as $item) {
            $item_arr[$item->id] =  $item;
        }

        $total_price = 0.00;
        $message = '';
        if(empty($shopping_cart)) {
            $message = 'Shoppong cart is empty, no need charge anything.';	       
   		  } else {
            foreach ($shopping_cart as $key => $value) {
              $total_price += $value * $item_arr[$key]->price;
            }

            // Set your secret key: remember to change this to your live secret key in production
            // See your keys here: https://dashboard.stripe.com/account/apikeys
            \Stripe\Stripe::setApiKey("sk_test_trFpSDiaXcyzPoFXTNwe6Jj6");

            // Token is created using Checkout or Elements!
            // Get the payment token ID submitted by the form:
            $token = $_POST['stripeToken'];

            // Charge the user's card:
            $charge = \Stripe\Charge::create(array(
              "amount" => $total_price * 100,
              "currency" => "sgd",
              "description" => "Example charge",
              "source" => $token,
            ));

            // clear shopping cart if payment successful
            if('succeeded' == $charge['status']){
              $_SESSION['shopping_cart'] = null;
              $message = "Paid !";

              // persistence transaction
              $em = $this->getDoctrine()->getManager();
              $order = new Order(json_encode($shopping_cart));
              $em->persist($order);
              $em->flush();
            } else {
              $message = "Payment failed";
            }
        }
        
        return $this->render('shop/result.html.twig', [
            'message' => $message,
            'total_price' => $total_price,
            'shopping_cart' => $shopping_cart,
            'items' => $item_arr,
        ]);
    }
}
